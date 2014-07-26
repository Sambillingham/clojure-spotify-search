(ns clojure-spotify-search.cs
    (:use [jayq.core :only [$]])
    (:require [jayq.core :as jq]
              [ajax.core :refer [GET]]))

(def $clickhere ($ :#clickhere))
(def $prevent-default ($ :#google-link))

(jq/bind $clickhere :click (fn [e] (js/console.log "Clicked!!")))

(jq/bind $prevent-default :click (fn [e] (.preventDefault e)))

(defn handler [response]
  (js/console.log  (str response)))

(defn error-handler [{:keys [status status-text]}]
  (.log js/console (str "something bad happened: " status " " status-text)))

(GET "https://api.spotify.com/v1/search"
  {:params {:q "taking back sunday"
            :type "track"
            :limit 5}
    :handler handler
    :error-handler error-handler})