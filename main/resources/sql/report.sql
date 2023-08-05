DROP FUNCTION get_user_stats(uuid,timestamp without time zone,timestamp without time zone);
create or replace function get_user_stats(user_uuid uuid ,  start_date timestamp, finish_date timestamp)
returns TABLE (
			login varchar,
			name varchar,
			hours numeric,
			workcost decimal(10,2)
		)
language plpgsql as $$
Begin
return query (select u.login,p.name,sum(EXTRACT(
        HOUR FROM r.end_timestamp - (r.begin_timestamp)
    )) , sum(r.work_cost)
                                 from public.user u
	left join record r on (r.user_id = u.id )
	left join public.project p on r.project_id = p.id
	where u.uuid = user_uuid and
	r.begin_timestamp >= start_date and r.end_timestamp <= finish_date
	group by u.login, p.name);
END
$$;