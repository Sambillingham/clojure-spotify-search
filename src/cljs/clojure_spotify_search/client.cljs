(ns clojure-spotify-search.cs
    (:use [jayq.core :only [$]])
    (:require [jayq.core :as jq]))

(def $clickhere ($ :#clickhere))
(def $prevent-default ($ :#google-link))

(jq/bind $clickhere :click (fn [e] (js/console.log "Clicked!!")))

(jq/bind $prevent-default :click (fn [e] (.preventDefault e)))