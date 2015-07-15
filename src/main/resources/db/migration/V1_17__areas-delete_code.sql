update documents
   set indexed = false
     , area    = 8
 where area in (1, 2, 3, 4);

delete from areas
 where area_id in (1, 2, 3, 4);
