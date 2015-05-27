(defproject summarizer "0.1.0-SNAPSHOT"
  :description "A simple text summarizer software and library written in Clojure"
  :url "https://github.com/aghasemi/ClojureTextSummarzier"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
                  [org.clojure/clojure "1.6.0"]
                  [clojure-opennlp "0.3.3"]
                  [org.clojure/math.numeric-tower "0.0.4"]

                  ]
  :plugins [[lein-exec "0.3.4"]]
  :main ^:skip-aot summarizer.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
