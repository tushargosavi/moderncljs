(ns modern-cljs.shopping
  (:require-macros [hiccups.core :as h])
  (:require [domina :as dom]
            [domina.events :as ev]
            [hiccups.runtime :as hiccupsrt]
            [shoreleave.remotes.http-rpc :refer [remote-callback]]
            [cljs.reader :refer [read-string]]))


(defn get-val [id]
  (read-string (dom/value (dom/by-id id))))

(defn calculate-local [quantity price tax discount]
  (update-total (-> (* quantity price)
                    (* (+  1 (/ tax 100)))
                    (- discount))))

(defn calculate-remote [quantity price tax discount]
  (remote-callback :calculate
                   [quantity price tax discount]
                   #(update-total %)))

(defn update-total [total]
  (dom/set-value! (dom/by-id "total") (.toFixed total 2)))

(defn calculate []
  (let [quantity (get-val "quantity")
        price (get-val "price")
        tax (get-val "tax")
        discount (get-val "discount")]
    (calculate-remote quantity price tax discount)))


(defn add-help []
  (dom/append! (dom/by-id "shoppingForm")
               (h/html [:div.help "Click to calculate"])))


(defn remove-help []
  (dom/destroy! (dom/by-class "help")))

(defn ^:export init []
  (when (and js/document
           (.-getElementById js/document))
    (ev/listen! (dom/by-id "calc") :click calculate)
    (ev/listen! (dom/by-id "calc") :mouseover add-help)
    (ev/listen! (dom/by-id "calc") :mouseout remove-help)))


;;(set! (.-onload js/window) init)
