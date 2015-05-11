(ns modern-cljs.core
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [modern-cljs.login :refer [authenticate-user]]))


(defroutes app-routes
  (GET "/" [] "<p>Hello from compojure</p>")
  (POST "/login" [email password] (authenticate-user email password))
  (route/resources "/")
  (route/not-found "Page not found"))

(def handler
  (handler/site app-routes))

