package com.example.dicoding_submisi2;

import java.util.List;

public class DetailSerialTvResult {


    /**
     * backdrop_path : /rL5f718Nlnl10N4LnXFVxRO2XQz.jpg
     * created_by : []
     * episode_run_time : [24]
     * first_air_date : 2004-10-05
     * genres : [{"id":10759,"name":"Action & Adventure"},{"id":16,"name":"Animation"},{"id":10765,"name":"Sci-Fi & Fantasy"}]
     * homepage : http://www.tv-tokyo.co.jp/anime/bleach
     * id : 30984
     * in_production : false
     * languages : ["ja"]
     * last_air_date : 2012-03-27
     * last_episode_to_air : {"air_date":"2012-03-27","episode_number":366,"id":1517562,"name":"Changing History, Unchanging Heart","overview":"","production_code":"","season_number":1,"show_id":30984,"still_path":null,"vote_average":0,"vote_count":0}
     * name : Bleach
     * next_episode_to_air : null
     * networks : [{"name":"TV Tokyo","id":98,"logo_path":"/kGRavMqgyx4p2X4C96bjRCj50oI.png","origin_country":"JP"}]
     * number_of_episodes : 366
     * number_of_seasons : 1
     * origin_country : ["JP"]
     * original_language : ja
     * original_name : ブリーチ
     * overview : For as long as he can remember, Ichigo Kurosaki has been able to see ghosts. But when he meets Rukia, a Soul Reaper who battles evil spirits known as Hollows, he finds his life is changed forever. Now, with a newfound wealth of spiritual energy, Ichigo discovers his true calling: to protect the living and the dead from evil.
     * popularity : 129.155
     * poster_path : /jLKCX4hDP5DbcsPHpOSs6CMWoNe.jpg
     * production_companies : [{"id":3234,"logo_path":"/o17H0aZzAn0qgVHY2IO3oDXVoBH.png","name":"Pierrot","origin_country":"JP"}]
     * seasons : [{"air_date":"2005-06-12","episode_count":2,"id":42575,"name":"Specials","overview":"","poster_path":null,"season_number":0},{"air_date":"2004-10-05","episode_count":366,"id":42567,"name":"Season 1","overview":"","poster_path":"/2qaPoZk6SmC8gnEYJJfms0I7tOg.jpg","season_number":1}]
     * status : Ended
     * type : Scripted
     * vote_average : 7.8
     * vote_count : 151
     */

    private String backdrop_path;
    private String first_air_date;
    private String homepage;
    private int id;
    private boolean in_production;
    private String last_air_date;
    private LastEpisodeToAirBean last_episode_to_air;
    private String name;
    private Object next_episode_to_air;
    private int number_of_episodes;
    private int number_of_seasons;
    private String original_language;
    private String original_name;
    private String overview;
    private double popularity;
    private String poster_path;
    private String status;
    private String type;
    private double vote_average;
    private int vote_count;
    private List<?> created_by;
    private List<Integer> episode_run_time;
    private List<GenresBean> genres;
    private List<String> languages;
    private List<NetworksBean> networks;
    private List<String> origin_country;
    private List<ProductionCompaniesBean> production_companies;
    private List<SeasonsBean> seasons;

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIn_production() {
        return in_production;
    }

    public void setIn_production(boolean in_production) {
        this.in_production = in_production;
    }

    public String getLast_air_date() {
        return last_air_date;
    }

    public void setLast_air_date(String last_air_date) {
        this.last_air_date = last_air_date;
    }

    public LastEpisodeToAirBean getLast_episode_to_air() {
        return last_episode_to_air;
    }

    public void setLast_episode_to_air(LastEpisodeToAirBean last_episode_to_air) {
        this.last_episode_to_air = last_episode_to_air;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getNext_episode_to_air() {
        return next_episode_to_air;
    }

    public void setNext_episode_to_air(Object next_episode_to_air) {
        this.next_episode_to_air = next_episode_to_air;
    }

    public int getNumber_of_episodes() {
        return number_of_episodes;
    }

    public void setNumber_of_episodes(int number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
    }

    public int getNumber_of_seasons() {
        return number_of_seasons;
    }

    public void setNumber_of_seasons(int number_of_seasons) {
        this.number_of_seasons = number_of_seasons;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public List<?> getCreated_by() {
        return created_by;
    }

    public void setCreated_by(List<?> created_by) {
        this.created_by = created_by;
    }

    public List<Integer> getEpisode_run_time() {
        return episode_run_time;
    }

    public void setEpisode_run_time(List<Integer> episode_run_time) {
        this.episode_run_time = episode_run_time;
    }

    public List<GenresBean> getGenres() {
        return genres;
    }

    public void setGenres(List<GenresBean> genres) {
        this.genres = genres;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public List<NetworksBean> getNetworks() {
        return networks;
    }

    public void setNetworks(List<NetworksBean> networks) {
        this.networks = networks;
    }

    public List<String> getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(List<String> origin_country) {
        this.origin_country = origin_country;
    }

    public List<ProductionCompaniesBean> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(List<ProductionCompaniesBean> production_companies) {
        this.production_companies = production_companies;
    }

    public List<SeasonsBean> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<SeasonsBean> seasons) {
        this.seasons = seasons;
    }

    public static class LastEpisodeToAirBean {
        /**
         * air_date : 2012-03-27
         * episode_number : 366
         * id : 1517562
         * name : Changing History, Unchanging Heart
         * overview :
         * production_code :
         * season_number : 1
         * show_id : 30984
         * still_path : null
         * vote_average : 0
         * vote_count : 0
         */

        private String air_date;
        private int episode_number;
        private int id;
        private String name;
        private String overview;
        private String production_code;
        private int season_number;
        private int show_id;
        private Object still_path;
        private int vote_average;
        private int vote_count;

        public String getAir_date() {
            return air_date;
        }

        public void setAir_date(String air_date) {
            this.air_date = air_date;
        }

        public int getEpisode_number() {
            return episode_number;
        }

        public void setEpisode_number(int episode_number) {
            this.episode_number = episode_number;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getProduction_code() {
            return production_code;
        }

        public void setProduction_code(String production_code) {
            this.production_code = production_code;
        }

        public int getSeason_number() {
            return season_number;
        }

        public void setSeason_number(int season_number) {
            this.season_number = season_number;
        }

        public int getShow_id() {
            return show_id;
        }

        public void setShow_id(int show_id) {
            this.show_id = show_id;
        }

        public Object getStill_path() {
            return still_path;
        }

        public void setStill_path(Object still_path) {
            this.still_path = still_path;
        }

        public int getVote_average() {
            return vote_average;
        }

        public void setVote_average(int vote_average) {
            this.vote_average = vote_average;
        }

        public int getVote_count() {
            return vote_count;
        }

        public void setVote_count(int vote_count) {
            this.vote_count = vote_count;
        }
    }

    public static class GenresBean {
        /**
         * id : 10759
         * name : Action & Adventure
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class NetworksBean {
        /**
         * name : TV Tokyo
         * id : 98
         * logo_path : /kGRavMqgyx4p2X4C96bjRCj50oI.png
         * origin_country : JP
         */

        private String name;
        private int id;
        private String logo_path;
        private String origin_country;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLogo_path() {
            return logo_path;
        }

        public void setLogo_path(String logo_path) {
            this.logo_path = logo_path;
        }

        public String getOrigin_country() {
            return origin_country;
        }

        public void setOrigin_country(String origin_country) {
            this.origin_country = origin_country;
        }
    }

    public static class ProductionCompaniesBean {
        /**
         * id : 3234
         * logo_path : /o17H0aZzAn0qgVHY2IO3oDXVoBH.png
         * name : Pierrot
         * origin_country : JP
         */

        private int id;
        private String logo_path;
        private String name;
        private String origin_country;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLogo_path() {
            return logo_path;
        }

        public void setLogo_path(String logo_path) {
            this.logo_path = logo_path;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOrigin_country() {
            return origin_country;
        }

        public void setOrigin_country(String origin_country) {
            this.origin_country = origin_country;
        }
    }

    public static class SeasonsBean {
        /**
         * air_date : 2005-06-12
         * episode_count : 2
         * id : 42575
         * name : Specials
         * overview :
         * poster_path : null
         * season_number : 0
         */

        private String air_date;
        private int episode_count;
        private int id;
        private String name;
        private String overview;
        private Object poster_path;
        private int season_number;

        public String getAir_date() {
            return air_date;
        }

        public void setAir_date(String air_date) {
            this.air_date = air_date;
        }

        public int getEpisode_count() {
            return episode_count;
        }

        public void setEpisode_count(int episode_count) {
            this.episode_count = episode_count;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public Object getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(Object poster_path) {
            this.poster_path = poster_path;
        }

        public int getSeason_number() {
            return season_number;
        }

        public void setSeason_number(int season_number) {
            this.season_number = season_number;
        }
    }
}
