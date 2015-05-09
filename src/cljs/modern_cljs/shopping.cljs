(ns modern-cljs.shopping
  (:require [domina :as dom]
            [domina.events :as ev]))


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

(defn ^:export init []
  (if (and js/document
           (.-getElementById js/document))
    (ev/listen! (dom/by-id "calc") :click calculate)))

;;(set! (.-onload js/window) init)
