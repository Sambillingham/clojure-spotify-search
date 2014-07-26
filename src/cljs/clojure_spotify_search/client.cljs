(ns clojure-spotify-search.cs
    (:require [ajax.core :refer [GET]]
              [dommy.utils :as utils]
              [dommy.core :as dommy])
    (:use-macros [dommy.macros :only [node sel sel1 deftemplate]]))

(dommy/listen! (sel1 :#google-link) :click (fn [e] (.preventDefault e)))

(defn start-search [event]
    (search-spotify (dommy/value (sel1 :.search-field))))

(dommy/listen! (sel1 :.search) :click start-search)

(defn return-tracks [tracks]
  ((tracks "tracks") "items"))

(defn error-handler [{:keys [status status-text]}]
  (.log js/console (str "something bad happened: " status " " status-text)))

(deftemplate simple-template [track]
  [:.track
    [:h2 (track "name")]
    [:a {:href ((track "external_urls") "spotify")} "Play"]
    [:img {:src "/img/track.jpg"} :alt "This is an image"]])

(defn append-track [track]
  (dommy/append! (sel1 :.tracks) (simple-template track)))

(defn log-track [track]
  (js/console.log (track "name")))

(defn loop-tracks [response]
  (dotimes [i (count (return-tracks response))]
    (append-track ((return-tracks response) i))))

(defn search-spotify [term]
(GET "https://api.spotify.com/v1/search"
  {:params {:q term
            :type "track"
            :limit 10}
    :handler loop-tracks
    :error-handler error-handler}))

