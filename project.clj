(defproject modern-cljs "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :source-paths ["src/clj", "src/cljs"]
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.3.4"]
                 [domina "1.0.3"]
                 [hiccups "0.3.0"]
                 [com.cemerick/valip "0.3.2"]
                 ;;[com.cemerick/shoreleave-remote-ring "0.0.2"]
                 [org.clojars.magomimmo/shoreleave-remote-ring "0.3.1-SNAPSHOT"]
                 [org.clojars.magomimmo/shoreleave-remote "0.3.1-SNAPSHOT"]
                 [org.clojure/clojurescript "0.0-2069"]
                 ;;[org.clojure/clojurescript "0.0-3211"]
                 ]

  :plugins [[lein-cljsbuild "1.0.0"]
            [lein-ring "0.9.3"]]

  :cljsbuild {
              :crossovers [valip.core valip.predicates modern-cljs.login.validators]

              :builds
              {:dev
               {
                :source-paths ["src/brepl" "src/cljs"]
                :compiler {
                           :output-to "resources/public/js/modern.js"
                           :optimizations :whitespace
                           :pretty-print true
                           }}

               :prod
               {;; clojurescript source code path
                :source-paths ["src/cljs"]

                ;; Google Closure Compiler options
                :compiler {;; the name of emitted JS script file
                           :output-to "resources/public/js/modern_prod.js"

                           ;; advanced optimization
                           :optimizations :advanced

                           ;; no need prettyfication
                           :pretty-print false}}}}

 :ring {:handler modern-cljs.remotes/app})
