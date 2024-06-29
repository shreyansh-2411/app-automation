package main.deserialisationobjects;

import lombok.Getter;

import java.util.List;

@Getter
public class getContestResponse {
    private String id;
    private String matchId;
    private String name;
    private String startTime;
    private String endTime;
    private String status;
    private configuration configuration;
    private winningTemplate winningTemplate;
    private match match;

    @Getter
    public static class configuration{
        private String entryFee;
        private String maxUsersAllowed;
        private String playersPerInning;
        private String minPlayersPerTeam;
        private String totalBudgetPerInning;
    }

    @Getter
    public static class winningTemplate {
        private String totalRewardAmount;
        private String winnerRewardAmount;
        private List<rewardDistributionTable> rewardDistributionTable;
        @Getter
        public static class rewardDistributionTable{
            private int fromRank;
            private int toRank;
            private int bolt;
            private int star;
        }
    }

    @Getter
    public static class match {
        private String isLineupAnnounced;
        private String name;
        private String startTime;
        private team team1;
        private team team2;
        private tournament tournament;
        private List<innings> innings;
        @Getter
        public static class team{
            private String shortName;
            private String imageUrl;
        }
        @Getter
        public static class tournament{
            private String id;
            private String name;
            private String imageUrl;
        }
        @Getter
        public static class innings{
            private String id;
            private String isRoundLocked;
            private battingTeam battingTeam;
        }
        @Getter
        public static class battingTeam{
            private String id;
            private String shortName;
        }
    }


}
