(ns cljc.validation
  (:require [clojure.string :as str]))

(declare get-pure-url)

(defn username?
  "Usernames can only contain letters, digits and underscores, and should be between 2 and 15 characters long. Please choose another."
  [username]
  (and (not (str/blank? username))
       (re-matches #"[a-zA-Z_0-9]{2,15}" username)))

(defn password?
  "Passwords should be between 8 and 128 characters long. Please choose another."
  [password]
  (and (not (str/blank? password))
       (re-matches #".{8,128}" password)))

(defn url?
  [url]
  (or (str/blank? url)
      (and
        (re-matches #"^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]" url)
        (>= (count (str/split (get-pure-url url) #"[.]")) 2)
        (>= (count (last (str/split (get-pure-url url) #"[.]"))) 2))))

(defn email?
  [email]
  (or (str/blank? email)
      (re-matches #"(([^<>()\[\]\\.,;:\s@\"]+(\.[^<>()\[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))" email)))

(defn github-or-twitter?
  [username]
  (or (str/blank? username)
      (and (not (url? username))
           (not (str/includes? username " "))
           (re-matches #".{1,40}" username))))

(defn about?
  [about]
  (or (str/blank? about)
      (and (>= (count about) 0)
           (<= (count about) 500))))

(defn show-email??
  [show-email-option]
  (contains? #{"yes" "no"} show-email-option))

(defn submit-type?
  [type]
  (contains? #{"story" "ask" "job" "event"} type))

(defn submit-title?
  [title]
  (and (not (str/blank? title))
       (<= (count title) 80)))

(defn submit-url?
  [url]
  (and (not (str/blank? url))
       (<= (count url) 500)
       (re-matches #"^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]" url)
       (>= (count (str/split (get-pure-url url) #"[.]")) 2)
       (>= (count (last (str/split (get-pure-url url) #"[.]"))) 2)))

(defn submit-text?
  [text]
  (and (not (str/blank? text))
       (<= (count text) 2500)))

(defn submit-day?
  [day]
  #?(:clj  (and (>= (Integer/parseInt day) 1)
                (<= (Integer/parseInt day) 31))
     :cljs (and (>= (js/parseInt day) 1)
                (<= (js/parseInt day) 31))))

(defn submit-month?
  [month]
  #?(:clj  (and (>= (Integer/parseInt month) 1)
                (<= (Integer/parseInt month) 12))
     :cljs (and (>= (js/parseInt month) 1)
                (<= (js/parseInt month) 12))))

(defn submit-year?
  [year]
  #?(:clj  (and (>= (Integer/parseInt year) 2016)
                (<= (Integer/parseInt year) 2056))
     :cljs (and (>= (js/parseInt year) 2016)
                (<= (js/parseInt year) 2056))))

(defn submit-city?
  [city]
  (and (not (str/blank? city))
       (<= (count city) 40)))

(defn submit-country?
  [country]
  (and (not (str/blank? city))
       (<= (count country) 40)))

(defn get-pure-url
  [url]
  (let [s (str/replace url #"^(https?)://(www.)?" "")]
    (if-let [index (str/index-of s "/")]
      (.substring s 0 index)
      s)))
