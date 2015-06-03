(ns summarizer.core
  (:gen-class)
  (:use
    [opennlp.nlp]
    )
    (:require
      [clojure.math.numeric-tower :as math]
      [clojure.core.reducers :as r]
      )
)

;Define tokenizer and sentence detector from OpenNLP
(def get-sentences (make-sentence-detector "models/en-sent.bin"))
(def tokenize (make-tokenizer "models/en-token.bin"))

;Returns the number of repetitions of item x in collection coll
(defn number-of-repetitions-of [x coll] (
                                           count (filter (fn [y] (= x y)) coll)
                                           )
  )

;Returns a hash-map mapping each unique item in coll to its number of appearances in coll
(defn count-repetitions-of-each-item-in-collection [coll]
  (
    into {} ;To convert to hash-map
    (
      let [coll-as-set (set coll)]
      (
         r/map (fn [x] [ x   (number-of-repetitions-of x coll)])
         coll-as-set
        )
      )

    )

  )

;Computes the score of a sentences based on how frequent its constituent words are in the whole text.
(defn sentence-score [sentence word-scores]
  (let [sentence-tokens (tokenize sentence)]
    (reduce + (r/map
        (fn [x] (
                  let [score (get word-scores x)]
                  (if (nil? score) 0 score)
                  )
          )
        sentence-tokens
        )
      )

    )
  )

;Computes the score of each sentence in a text
(defn compute-sentence-scores [text word-scores]
  (let [scores (count-repetitions-of-each-item-in-collection (tokenize text))]
    (let [sentences (get-sentences text)]
     (r/map
       (fn [sentence] (sentence-score sentence scores))
       sentences

       )

     )
    )
  )

;main function. Finds the k best sentences in the text file given as input
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
    (let [text (slurp (first args))
           tokens (tokenize text)
          word-counts (count-repetitions-of-each-item-in-collection tokens)
          sentences (get-sentences text)]
      (println
        (let [ sentence-scores (map (fn [sentence] (sentence-score sentence word-counts)) sentences)
               indexed-sentence-scores (map vector (range (count sentence-scores)) sentence-scores)
               sorted-sentence-scores (sort-by second > indexed-sentence-scores)
               number-of-sentences-in-summarized-text
               (math/ceil
                 (if (> (count args) 1) (* (count sentences) (Double/parseDouble (last args))) (* (count sentences) 0.5)))
               ]
          (clojure.string/join "" (map
             (fn [x] (nth sentences x))
             (map (fn [x] (first x))
               (sort-by first (take number-of-sentences-in-summarized-text sorted-sentence-scores)))
             )
            )
          )
        )
      )
  )