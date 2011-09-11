(ns is-saga-open.core
  (:use compojure.core date-clj [hiccup core page-helpers])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]))
(defn open [h1 m1 h2 m2]
  (between? (today) 
            (set-date (today) :hour h1 :minute m1 :second 0) 
            (set-date (today) :hour h2 :minute m2 :second 0))
  )
(defn weekends []
  (or (open 17 0 19 0) (open 10 30 13 0) 
  ))
(defn weekday []
  (or (open 7 30 16 0) (open 17 0 19 0)
    ))
(defn night-truck []
  (if (is? (today) :monday) false (between? (today) (set-date (today) :hour 23 :minute 0) (set-date (tomorrow) :hour 3 :minute 0) )
  ))

(defn index-pg []
  (html5 [:head [:title "Is SAGA Open?"] (include-css "/css/style.css")]
          [:body [:h1 (if (is? (today) :weekend) (weekends) (weekday))]]
  ))

(defroutes main-routes
  (GET "/" [] (index-pg))
  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (handler/site main-routes))
