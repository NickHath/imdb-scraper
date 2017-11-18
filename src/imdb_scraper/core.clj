(ns imdb-scraper.core
  (:require [falcon.core :as falcon]))

; testing data for concurrency
; 457 reviews from 46 pages 
(def reviewUrls `("http://www.imdb.com/title/tt0093870/reviews?start=0" "http://www.imdb.com/title/tt0093870/reviews?start=10" "http://www.imdb.com/title/tt0093870/reviews?start=20" "http://www.imdb.com/title/tt0093870/reviews?start=30" "http://www.imdb.com/title/tt0093870/reviews?start=40" "http://www.imdb.com/title/tt0093870/reviews?start=50" "http://www.imdb.com/title/tt0093870/reviews?start=60" "http://www.imdb.com/title/tt0093870/reviews?start=70" "http://www.imdb.com/title/tt0093870/reviews?start=80" "http://www.imdb.com/title/tt0093870/reviews?start=90" "http://www.imdb.com/title/tt0093870/reviews?start=100" "http://www.imdb.com/title/tt0093870/reviews?start=110" "http://www.imdb.com/title/tt0093870/reviews?start=120" "http://www.imdb.com/title/tt0093870/reviews?start=130" "http://www.imdb.com/title/tt0093870/reviews?start=140" "http://www.imdb.com/title/tt0093870/reviews?start=150" "http://www.imdb.com/title/tt0093870/reviews?start=160" "http://www.imdb.com/title/tt0093870/reviews?start=170" "http://www.imdb.com/title/tt0093870/reviews?start=180" "http://www.imdb.com/title/tt0093870/reviews?start=190" "http://www.imdb.com/title/tt0093870/reviews?start=200" "http://www.imdb.com/title/tt0093870/reviews?start=210" "http://www.imdb.com/title/tt0093870/reviews?start=220" "http://www.imdb.com/title/tt0093870/reviews?start=230" "http://www.imdb.com/title/tt0093870/reviews?start=240" "http://www.imdb.com/title/tt0093870/reviews?start=250" "http://www.imdb.com/title/tt0093870/reviews?start=260" "http://www.imdb.com/title/tt0093870/reviews?start=270" "http://www.imdb.com/title/tt0093870/reviews?start=280" "http://www.imdb.com/title/tt0093870/reviews?start=290" "http://www.imdb.com/title/tt0093870/reviews?start=300" "http://www.imdb.com/title/tt0093870/reviews?start=310" "http://www.imdb.com/title/tt0093870/reviews?start=320" "http://www.imdb.com/title/tt0093870/reviews?start=330" "http://www.imdb.com/title/tt0093870/reviews?start=340" "http://www.imdb.com/title/tt0093870/reviews?start=350" "http://www.imdb.com/title/tt0093870/reviews?start=360" "http://www.imdb.com/title/tt0093870/reviews?start=370" "http://www.imdb.com/title/tt0093870/reviews?start=380" "http://www.imdb.com/title/tt0093870/reviews?start=390" "http://www.imdb.com/title/tt0093870/reviews?start=400" "http://www.imdb.com/title/tt0093870/reviews?start=410" "http://www.imdb.com/title/tt0093870/reviews?start=420" "http://www.imdb.com/title/tt0093870/reviews?start=430" "http://www.imdb.com/title/tt0093870/reviews?start=440" "http://www.imdb.com/title/tt0093870/reviews?start=450"))

; writes a lazy sequence to a filename
(defn writeToFile [filename, lazySeq]
  (spit filename (pr-str lazySeq) :append true))

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

; get html and write reviews to robocop_reviews for all urls in pre-defined list
(defn getAllReviews []
  (doseq 
    [url reviewUrls]
    (writeToFile "robocop_reviews.txt" (getReviewHtml url))))

;; time how long getAllReviews takes synchronously
(defn -main
  []
    (time (getAllReviews)))