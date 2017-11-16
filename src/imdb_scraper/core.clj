(ns imdb-scraper.core
  (:require [clj-http.client :as client]
            [falcon.core :as falcon]))

; testing data for concurrency
; 100 reviews from 10 pages 
(def reviewUrls `("http://www.imdb.com/title/tt0093870/reviews?start=0" "http://www.imdb.com/title/tt0093870/reviews?start=10" "http://www.imdb.com/title/tt0093870/reviews?start=20" "http://www.imdb.com/title/tt0093870/reviews?start=30" "http://www.imdb.com/title/tt0093870/reviews?start=40" "http://www.imdb.com/title/tt0093870/reviews?start=50" "http://www.imdb.com/title/tt0093870/reviews?start=60" "http://www.imdb.com/title/tt0093870/reviews?start=70" "http://www.imdb.com/title/tt0093870/reviews?start=80" "http://www.imdb.com/title/tt0093870/reviews?start=90"))

; writes a lazy sequence to a filename
(defn writeToFile [filename, lazySeq]
  (spit filename (pr-str lazySeq)))

; takes a falcon html map and returns all containing text
(defn extractReviewTxt [html]
  (for [review html]
       (get review :text)))

; fetches and extracts text of p elements for a url
(defn getReviewHtml [url]
  (extractReviewTxt
    (falcon/select
      (falcon/parse url)
      "p")))

(defn -main
  []
  (writeToFile
    "robocop_page_6.txt"
    (getReviewHtml "http://www.imdb.com/title/tt0093870/reviews?start=50")))
