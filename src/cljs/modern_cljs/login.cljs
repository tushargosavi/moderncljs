(ns modern-cljs.login
  (:require-macros [hiccups.core :refer [html]])
  (:require [domina :refer [value attr by-id by-class destroy! append! prepend!]]
            [domina.events :as ev]
            [hiccups.runtime]
            [modern-cljs.login.validators :refer [user-credential-errors]]))

(defn get-value [name]
  (value (by-id name)))

(defn validate-form [e email password]
  (if-let [{e-errs :email p-errs :password} (user-credential-errors (value email) (value password))]
    (if (or e-err p-err)
      (do
        (destroy! (by-class "help"))
        (ev/prevent-default e)
        (append! (by-id "loginForm") (html [:div.help "Please complete form"])))
      (ev/prevent-default e))
    true))

(defn validate-dbg [e email password]
  (do
    (.log js/console "validate function called")
    (validate-form e email password)))

(defn validate-email [email]
  (destroy! (by-class "email"))
  (if-let [errors (:email (user-credential-errors (value email) nil))]
    (do
      (prepend! (by-id "loginForm") (html [:div.help.email (first errors)]))
      false)
    true))

(defn validate-password [password]
  (destroy! (by-class "password"))
  (if-let [errors (:password (user-credential-errors nil (value password)))]
    (do
      (append! (by-id "loginForm") (html [:div.help.password (first errors)]))
      false)
    true))


(defn ^:export init []
  (if (and js/document
           (aget js/document "getElementById"))
    (let [email (by-id "email")
          password (by-id "password")]
      (ev/listen! (by-id "submit") :click (fn [e] validate-dbg e email password))
      (ev/listen! email :blur (fn [e] (validate-email email)))
      (ev/listen! password :blur (fn [e] (validate-password password))))))