select * from likes l
join users u on u.user_id = l.user_id
where u.username LIKE 'shurrik77'

select l.like_id, u.user_id, l.user_id, u.username from likes l
join users u on u.user_id = l.user_id
where u.username LIKE 'shurrik77'


select * from post p
            join subscriptions s on s.user_sub_id = p.user_id
            join users u on u.user_id = s.user_id
            join likes l on u.user_id = l.user_id
            where u.username = 'shurrik77'
            order by p.created_at desc;



--select count(p.post_id) from post p " +
 --           "join subscriptions s on s.user_sub_id = p.user_id " +
--            "join users u on u.user_id = s.user_id " +
--            "where u.username = ?