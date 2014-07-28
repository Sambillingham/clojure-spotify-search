(ns clojure-spotify-search.templates
    (:require [dommy.utils :as utils]
              [dommy.core :as dommy])
    (:use-macros [dommy.macros :only [deftemplate]]))

(deftemplate track-template [track]
  [:.track.animated.fadeIn
    [:a.image-link {:target "_blank" :href (track :play)}
      [:span.play-btn
        [:span.arrow-right]]
      [:img {:src (track :image)} :alt "This is an image"]]
    [:span.name (track :name)]
    [:span.artist (track :artist)]])

(deftemplate alert-error []
  [:.alert.error.animated.fadeInDown
    [:p "Opps! Looks like something has gone wrong try searching again"]])
