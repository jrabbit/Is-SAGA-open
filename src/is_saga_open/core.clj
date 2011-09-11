(ns is-saga-open.core
  (:use compojure.core date-clj [hiccup core page-helpers])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]))
(defn weekends []
  (or (between? (today) (set-date (today) :hour 17 :minute 0) (set-date (today) :hour 19 :minute 0))
      (between? (today) (set-date (today) :hour 10 :minute 30) (set-date (today) :hour 13 :minute 0))
  ))
(defn weekday []
  (or (between? (today) (set-date (today) :hour 7 :minute 30) (set-date (today) :hour 16 :minute 0))
      (between? (today) (set-date (today) :hour 17 :minute 0) (set-date (today) :hour 19 :minute 0))
    ))
(defn night-truck []
  (if (is? (today) :monday) false (between? (today) (set-date (today) :hour 23 :minute 0) (set-date (tomorrow) :hour 3 :minute 0) )
  ))

(if (is? (today) :weekend) (weekends) (weekday))


(defroutes main-routes
  (GET "/" [] "<h1>Hello World Wide Web!</h1>")
  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (handler/site main-routes))
