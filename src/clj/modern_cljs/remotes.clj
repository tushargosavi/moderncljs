(ns modern-cljs.remotes
  (:require [modern-cljs.core :refer [handler]]
            [compojure.handler :refer [site]]
            [shoreleave.middleware.rpc :refer [defremote wrap-rpc]]))


(defn calculate-total [quantity price tax discount]
  (-> (* quantity price)
      (* (+ 1 (/ tax 100)))
      (- discount)))

(comment
(defremote calculate [quantity price tax discount]
  (let [result (calculate-total quantity price tax discount)]
    (println "result is " result)
    result)))

(defremote calculate [quantity price tax discount]
  (-> (* quantity price)
      (* (+ 1 (/ tax 100)))
      (- discount)))

(def app (-> (var handler)
             (wrap-rpc)
             (site)))
