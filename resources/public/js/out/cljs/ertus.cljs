(ns cljs.ertus
  (:require-macros [secretary.core :refer [defroute]])
  (:require [goog.events :as events]
            [goog.dom :as dom]
            [secretary.core :as secretary]
            [reagent.core :as r]
            [dene])
  (:import goog.History
           goog.History.EventType))


(secretary/set-config! :prefix "#")

(def main-container
  (js/document.getElementById "mainContainerId"))

(defn set-html! [el content]
  (set! (.-innerHTML el) content))

(defn simple-component []
  [:div
   [:p "I am a component!"]
   [:p.someclass
    "I have " [:strong "bold"]
    [:span {:style {:color "red"}} " and red "] "text."]])

(defn table-ex
  []
  [:table
   [:tbody
    [:tr
     [:td]]
    [:tr
     [:td "username:"]
     [:td
      [:input {:type "text"}]]]
    [:tr
     [:td "password:"]
     [:td
      [:input {:type "password"}]]]
    [:tr
     [:td
      [:button {:id "submitId" :on-click (fn [_] (js/alert "Ertuss!!"))} "Submit"]]]]])

(defn ^:export render-simple []
  (r/render-component [table-ex] main-container))


(defn empty-ex
  [])

;(d/get-bla-bla-route)
;
;(defroute new "/new" []
;          (r/render-component [empty-ex] main-container))


(let [h (History.)]
  (goog.events/listen h EventType/NAVIGATE

                      #(secretary/dispatch! (.-token %)))
  (doto h
    (.setEnabled true)
    (.setToken (str js/window.location.pathname js/window.location.search))))