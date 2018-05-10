-- src/metcolor_api/sql/objects.sql
-- MetColor Objects

-- A ":result" value of ":*" specifies a vector of records
-- (as hashmaps) will be returned
-- :name all-objects :? :*
-- :doc Get all objects
-- SELECT *
-- FROM metcolordb1.public.obj_domcolor_tbl;

-- A ":result" value of ":1" specifies a single record
-- (as a hashmap) will be returned
-- :name object-by-id :? :1
-- :doc Get object by object_id
SELECT *
FROM metcolordb1.public.obj_domcolor_tbl
WHERE object_id = :object_id;

-- Let's specify some columns with the
-- identifier list parameter type :i* and
-- use a value list parameter type :v* for SQL IN()
-- :name objects-by-ids-specify-cols :? :*
-- :doc Objects with returned columns specified
-- SELECT :i * :cols
-- FROM metcolordb1.public.obj_domcolor_tbl
-- WHERE object_id IN (:v * :object_ids);
