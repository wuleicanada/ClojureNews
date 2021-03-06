(ns clj.dao.upvote
  (:refer-clojure :exclude [sort find])
  (:require [monger.collection :as mc]
            [monger.operators :refer :all]
            [monger.query :refer :all]
            [clj.dao.db-conf :as db]
            [clj.util.entity :as entity-util])
  (:import (org.bson.types ObjectId)))

;; upvote Collection/Table
(def coll "upvote")

(defn create-upvote
  [^String created-by
   ^String type
   ^String entry-id
   ^String comment-id]
  (mc/insert-and-return db/clojure-news coll (entity-util/upvote created-by type entry-id comment-id)))

(defn find-by-created-by-and-comment-id
  [^String created-by
   ^String comment-id]
  (mc/find-one-as-map db/clojure-news coll {$and [{:created-by created-by}
                                                  {:comment-id comment-id}]}))

(defn find-by-created-by-and-entry-id-and-type
  [^String created-by
   ^String enry-id
   ^String type]
  (mc/find-one-as-map db/clojure-news coll {$and [{:created-by created-by}
                                                  {:entry-id enry-id}
                                                  {:type type}]}))

(defn find-by-type-and-entry-id
  [^String type
   ^String entry-id]
  (mc/find-maps db/clojure-news coll {:type     type
                                      :entry-id entry-id}))

(defn delete-upvotes-by-entry-id
  [^String entry-id]
  (mc/remove db/clojure-news coll {:entry-id entry-id}))

(defn get-upvotes-by-username-and-upvotes-in-it
  [username entries]
  (mc/find-maps db/clojure-news coll {$and [{:created-by username}
                                            {:entry-id {$in entries}}]}))

(defn find-story-upvote-by-created-by-and-entry-id
  [^String created-by
   ^String entry-id]
  (mc/find-one-as-map db/clojure-news coll {$and [{:created-by created-by}
                                                  {:entry-id entry-id}
                                                  {:type "story"}]}))

(defn find-ask-upvote-by-created-by-and-entry-id
  [^String created-by
   ^String entry-id]
  (mc/find-one-as-map db/clojure-news coll {$and [{:created-by created-by}
                                                  {:entry-id entry-id}
                                                  {:type "ask"}]}))

(defn find-comment-upvote-by-created-by-entry-id-and-comment-id
  [^String created-by
   ^String entry-id
   ^String comment-id]
  (mc/find-one-as-map db/clojure-news coll {$and [{:created-by created-by}
                                                  {:entry-id entry-id}
                                                  {:comment-id comment-id}]}))