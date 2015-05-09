(ns modern-cljs.login)

(defn validate-form []
  (let [email (.getElementById js/document "email")
        password (.getElementById js/document "password")]
    (if (and (> (count (.-value email)) 0)
             (> (count (.-value password)) 0))
      true
      (do (js/alert "Please complete the form")
          false))))

(defn validate-dbg []
  (do
    (.log js/console "validate function called")
    (validate-form)))

(defn init []
  (if (and js/document
           (.-getElementById js/document))
    (let [login-form (.getElementById js/document "loginForm")]
      (do
        (set! (.-onsubmit login-form) validate-dbg)
        (.log js/console "init function called")))))

(set! (.-onload js/window) init)
