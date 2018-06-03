package ru.firsov.javacore;

class Team {
    class Member {
        String name;
        int weight;
        int lengthPerson;
        double result;


        Member(String name, int weight, int length) {
            this.name = name;
            this.weight = weight;
            this.lengthPerson = length;
            this.result = weight / length;
        }
    }
    private String name;
    Member[] members = new Member[4];
    boolean[] teamResults1 = new boolean[members.length];
    boolean[] teamResults2 = new boolean[members.length];

    Team(String name) {
        this.name = name;
        members[0] = new Member("Kirill", 90, 190);
        members[1] = new Member("Egor", 63, 170);
        members[2] = new Member("Andrew", 90, 171);
        members[3] = new Member("Vadim", 50, 165);
    }

    void showResults(){
        System.out.println();
        System.out.println("Team " + name + " results:");
        for (int i = 0; i < members.length; i++) {
            System.out.println(members[i].name + " Have passed the first barrier with " + teamResults1[i] + " result");
            System.out.println(members[i].name + " Have passed the second barrier with " + teamResults2[i] + " result");
            System.out.println();
        }
    }

}
