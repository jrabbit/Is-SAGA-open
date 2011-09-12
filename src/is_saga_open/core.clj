(ns is-saga-open.core
  (:use compojure.core date-clj [hiccup core page-helpers])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]))
(def tz-offset 0)

(defn open [h1 m1 h2 m2]
  (between? (today) 
            (set-date (today) :hour (+ h1 tz-offset) :minute m1 :second 0) 
            (set-date (today) :hour (+ h2 tz-offset) :minute m2 :second 0))
  )
(defn weekends []
  (or (open 17 0 19 0) (open 10 30 13 0) 
  ))
(defn weekday []
  (or (open 7 30 16 0) (open 17 0 19 0)
    ))
(defn night-truck []
  (if (is? (today) :monday) false (between? (today) (set-date (today) :hour (+ 23 tz-offset) :minute 0 :second 0) (set-date (tomorrow) :hour (+ 3 tz-offset) :minute 0 :second 0) )
  ))

(defn yes []
  (html5 [:head [:title "Is SAGA Open?"]]
  (include-css "/css/yes.css") [:body [:h1 "Yes!"]]
  ))
(defn no []
  (html5 [:head [:title "Is SAGA Open?"]]
  (include-css "/css/no.css") [:body [:h1 "No! :("]
  (if (night-truck)
    [:h2 "But " (link-to "http://thenighttruck.com/" "The Night Truck")" is open"])]
  ))

(defroutes main-routes
  (GET "/" [] (if (if (is? (today) :weekend) (weekends) (weekday)) (yes) (no)))
  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (handler/site main-routes))
