(defproject is-saga-open "1.0.0-SNAPSHOT"
  :description "Webapp to tell if Hampshire College's Dining Commons are open"
  :dependencies [[org.clojure/clojure "1.2.1"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [compojure "0.6.4"]
                 [date-clj "1.0.1"]
                 [hiccup "0.3.6"]]
  :dev-dependencies [[lein-ring "0.4.5"]]
  :ring {:handler is-saga-open.core/app})