(defproject clojure-spotify-search "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2268"]
                 [ring-server "0.3.1"]
                 [compojure "1.1.8"]
                 [hiccup "1.0.0"]
                 [cljs-ajax "0.2.6"]
                 [prismatic/dommy "0.1.2"]]
  :plugins [[lein-cljsbuild "1.0.3"]
            [lein-ring "0.8.11"]]
  :min-lein-version "2.0.0"
  :uberjar-name "clojure-spotify-search-standalone.jar"
  :main clojure-spotify-search.server
  :aot [clojure-spotify-search.server]
  :profiles {:uberjar {:main clojure-spotify-search.server, :aot :all}}
  :ring {:handler clojure-spotify-search.server/app}
  :source-paths ["src/clj"]
  :cljsbuild
    {:builds
      [{:source-paths ["src/cljs"],
        :compiler
          {:pretty-print true,
           :output-to "resources/public/js/cljs.js",
           :optimizations :simple}}]})
