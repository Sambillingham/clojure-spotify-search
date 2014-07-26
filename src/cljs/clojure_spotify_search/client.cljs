(ns clojure-spotify-search.cs
    (:require [ajax.core :refer [GET]]
              [dommy.utils :as utils]
              [dommy.core :as dommy])
    (:use-macros [dommy.macros :only [node sel sel1]]))

(dommy/append! (sel1 :body) [:h1 "Eat some cake"])

(dommy/listen! (sel1 :#google-link) :click (fn [e] (.preventDefault e)))

(defn clickEvent [event]
    (.log js/console "You have clicked the button! Congratulations"))

(dommy/listen! (sel1 :#clickhere)
                :click clickEvent)

(js/console.log (get {:a 1 :b 2 } :a))

(defn append-track [track]
  (dommy/append! (sel1 :body) [:.track (str "Name: " ( track "name" )  " link: " ((track "external_urls") "spotify") "" )]))

(defn return-tracks [tracks]
  ((tracks "tracks") "items"))

(defn log-track [response]
  (js/console.log ((last (return-tracks response)) "name")))

;(defn handler [response]
;  (js/console.log  ((last ((response "tracks") "items")) "name")))

(defn error-handler [{:keys [status status-text]}]
  (.log js/console (str "something bad happened: " status " " status-text)))

(defn print-track [track]
  (js/console.log (str (track "name") (track "external_url") )))

;(append-track {"name" "sam" "external_url" "rodger"})

(defn loop-tracks [response]
  (dotimes [i (count (return-tracks response)) ]
    (append-track ((return-tracks response) i))))

;(js/console.log (str i ": " ((return-tracks response) i)
(js/console.log (subvec {"name" "sam" "external_url" "rodger"} "rodger"))


(GET "https://api.spotify.com/v1/search"
  {:params {:q "taking back sunday"
            :type "track"
            :limit 5}
    :handler loop-tracks
    :error-handler error-handler})
