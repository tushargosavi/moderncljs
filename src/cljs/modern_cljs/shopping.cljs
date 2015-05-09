(ns modern-cljs.shopping
  (:require-macros [hiccups.core :as h])
  (:require [domina :as dom]
            [domina.events :as ev]
            [hiccups.runtime]))


(defn get-val [id]
  (dom/value (dom/by-id id)))

(defn calculate []
  (let [quantity (get-val "quantity")
        price (get-val "price")
        tax (get-val "tax")
        discount (get-val "discount")]
    (dom/set-value! (dom/by-id "total") (-> (* quantity price)
                                    (* (+  1 (/ tax 100)))
                                    (- discount)
                                    (.toFixed 2)))
    false))

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
