package main.serialisationobjects;

import java.util.List;


public class innings {
    private List<contestInningTeam> contestInningTeam;

    public List<innings.contestInningTeam> getContestInningTeam() {
        return contestInningTeam;
    }

    public void setContestInningTeam(List<innings.contestInningTeam> contestInningTeam) {
        this.contestInningTeam = contestInningTeam;
    }

    public static class contestInningTeam{
        private Double budget;
        private Double playerId;
        private Double playerKpiId;
        private Double sequence;
        private Double teamId;

        public contestInningTeam(Double budget, Double playerId, Double playerKpiId, Double sequence, Double teamId){
            this.budget = budget;
            this.playerId = playerId;
            this.teamId = teamId;
            this.playerKpiId = playerKpiId;
            this.sequence = sequence;
        }
        public Double getBudget() {
            return budget;
        }

        public Double getTeamId() {
            return teamId;
        }

        public Double getSequence() {
            return sequence;
        }

        public Double getPlayerKpiId() {
            return playerKpiId;
        }

        public Double getPlayerId() {
            return playerId;
        }
    }

}
