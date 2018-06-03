package ru.firsov.javacore;


public class Course {
    private Team team;

    public Course(Team team) {
        this.team = team;
    }

    class barrier{
        int typeOfChallenge;  //тип препятствия 1 - Хождение по льду, 2 - лимбо
        double requirement;
        public barrier(int typeOfChallenge) {
            this.typeOfChallenge = typeOfChallenge;
            if (typeOfChallenge == 1){
                requirement = 60;  // отношение веса к высоте
            } else{
                requirement = 180;  // минимальный рост при котором возможно выполнение задания
            }
        }
        boolean passBarrier(double result){
            if (result < this.requirement){
                return true;
            }else{
                return false;
            }
        }

    }

    public void doIt(){
        barrier [] barriers = new barrier[2];
        barriers[0] = new barrier(1);
        barriers[1] = new barrier(2);
        boolean results;

        for (int i = 0; i < barriers.length; i++) {
            System.out.println();
            System.out.println("Barrier number " + i + "!");
            for (int j = 0; j < team.members.length; j++) {
                if (barriers[i].typeOfChallenge == 1){
                    results = barriers[i].passBarrier(team.members[j].weight);
                    team.teamResults1[j] = results;
                }
                else {
                    results = barriers[i].passBarrier(team.members[j].lengthPerson);
                    team.teamResults2[j] = results;
                }
                if (results){
                    System.out.println("Player " + team.members[j].name + " passed");
                }else{
                    System.out.println("Player " + team.members[j].name + " loose");
                }
            }
        }
    }
}
