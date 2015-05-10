(ns modern-cljs.login
  (:require-macros [hiccups.core :refer [html]])
  (:require [domina :as dom]
            [domina.events :as ev]
            [hiccups.runtime]))


(def ^:dynamic *password-re* #"^(?=.*\d).{4,8}$")

(def ^:dynamic *email-re* #"^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,4})$")

(defn get-value [name]
  (dom/value (dom/by-id name)))

(defn validate-form [e]
  (let [email (dom/by-id "email")
        password (dom/by-id "password")
        email-val (dom/value email)
        password-val (dom/value password)]
    (if (or (empty? email-val) (empty? password-val))
      (do
        (dom/destroy! (dom/by-class "help"))
        (ev/prevent-default e)
        (dom/append! (dom/by-id "loginForm") (html [:div.help "Please complete the form"])))
      (if (and (validate-email email) (validate-password password))
        true
        (ev/prevent-default e)))))

(defn validate-dbg [e]
  (do
    (.log js/console "validate function called")
    (validate-form e)))

(defn validate-email [email]
  (.log js/console "calling validate email")
  (dom/destroy! (dom/by-class "email"))
  (if (not (re-matches *email-re* (dom/value email)))
    (do
      (dom/prepend! (dom/by-id "loginForm") (html [:div.help.email "Wrong email"]))
      false)
    true))

(defn validate-password [passwd]
  (.log js/console "calling validate password")
  (dom/destroy! (dom/by-class "password"))
  (if (not (re-matches *password-re* (dom/value passwd)))
    (do
      (dom/prepend! (dom/by-id "loginForm") (html [:div.help.password "Wrong password"]))
      false)
    true))

(defn ^:export init []
  (if (and js/document
           (aget js/document "getElementById"))
    (let [email (dom/by-id "email")
          password (dom/by-id "password")]
      (ev/listen! (dom/by-id "submit") :click validate-dbg)
      (ev/listen! email :blur (fn [e] (validate-email email)))
      (ev/listen! password :blur (fn [e] (validate-password password))))))
