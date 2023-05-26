package Yacht;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class YachtDisplay {

    static Scanner sc;

    public YachtDisplay() {
        sc = new Scanner(System.in);
        System.out.println("★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆");
        GameManager.PrintFontCyanln("                 YACHT                 ");
        System.out.println("★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆");
        System.out.print("플레이어의 수를 입력해 주세요(종료 key는 0) : ");

        while (true) {
            int playerCount = sc.nextInt();
            if (playerCount > 1) {
                for (int i = 0; i < playerCount; i++)
                    new Player("Player_" + Integer.toString(i + 1));
                System.out.println("★☆★☆★☆★☆");
                System.out.println("게임을 시작합니다");
                System.out.println("★☆★☆★☆★☆");
                Play();
                break;
            } else if(playerCount == 0) {
                System.out.println("게임을 종료합니다.");
                break;
            }else {
                System.out.println("잘못된 숫자입니다. 플레이어의 수를 입력해 주세요 : ");
            }
        }
    }

    public void Play() {
        GameManager.GetInstance().GameStart();

        while (GameManager.GetInstance().IsGamePlay()) {
            _Turn();
            GameManager.GetInstance().EndTurn();
        }

        GameManager.GetInstance().PrintWinner();
    }

    private void _Turn() {

        Player nowPlayer = GameManager.GetInstance().GetNowPlayer();
        System.out.println();
        System.out.println(nowPlayer.GetName() + "의 차례");

        List<Integer> dices = new ArrayList<>();
        int count = 0;

        while (dices.size() < 5) {
            GameManager.PrintFontCyanln("주사위를 굴립니다. 남은 횟수는 " + (2 - count) + "번" + GameManager.RESET);
            // 주사위 굴리기
            dices = _DiceSet(dices);

            if (count > 1) {
                break;
            }

            System.out.println("현재 필드의 수");
            _PrintDices(dices);

            int[] sortedDiceData = _SortDiceData(dices);
            nowPlayer.PrintDeck(sortedDiceData);

            while (!_Keep(dices))
                ;

            _PrintDices(dices);

            count++;
        }
        if (count == 2) {
            System.out.println("현재 필드의 수");
            _PrintDices(dices);

            int[] sortedDiceData = _SortDiceData(dices);
            nowPlayer.PrintDeck(sortedDiceData);
        }
        int[] sortedDiceData = _SortDiceData(dices);
        String resPut;

        do {
            nowPlayer.PrintSelect();
            resPut = sc.next();

        } while (!nowPlayer.SetScore(nowPlayer, sortedDiceData, resPut));

        System.out.println();

    }


    static private List<Integer> _DiceSet(List<Integer> initial) {

        while (initial.size() < 5) {
            int rand = (int) (Math.random() * 6) + 1;
            initial.add(rand);

        }
        return initial;

    }

    static private void _PrintDices(List<Integer> dices) {

        for (int i = 0; i < dices.size(); ++i) {
            System.out.print(dices.get(i) + " ");
        }

        System.out.println("\n");
    }

    static private int[] _SortDiceData(List<Integer> dices) {
        int[] result = new int[6];

        for (int i = 0; i < dices.size(); i++) {
            result[dices.get(i) - 1]++;
        }

        return result;

    }

    private boolean _Keep(List<Integer> dices) {

        List<Integer> keep = new ArrayList<>();
        int keepCount = 0;

        while (keepCount < 5) {
            GameManager.PrintFontCyan("Keep하고자 하는 Index를 쓰세요. (없다면 0을 쓰세요): ");
            int num = 0;
            num = sc.nextInt() - 1;
            if (num == -1)
                break;

            // 실수로 동일한 넘버를 적었을 경우에도 오류를 출력
            if (num >= dices.size() || num < 0 || dices.get(num) < 0) {
                GameManager.PrintFontRedln("잘못된 Index를 작성했습니다. 다시 작성하세요. 없다면 0을 쓰세요.");
                continue;
            }

            keep.add(dices.get(num)); // keep 배열에는 dice의 결과들을 추가
            dices.set(num, dices.get(num) * -1); // keep index는 -1을 곱함
            keepCount++;

            System.out.println("현재 선택한 수");
            _PrintDices(keep);

        }

        if (keep.size() > 0) {
            System.out.println("현재 본인이 선택한 Index와 선택했던 Index는 아래와 같습니다.");
            System.out.println("아래의 수를 Keep한 다음 주사위를 굴릴려면 Y를(수정 불가)," + "수정하고 싶으면 Y를 제외한 다른 키를 누르세요.");
            _PrintDices(keep);
        } else {
            System.out.println(
                    "현재 아무런 Index도 선택하지 않았습니다. 이대로 다음 주사위를 굴릴려면 Y를(수정 불가)," + "수정하고 싶으면 Y를 제외한 다른 키를 누르세요.");

        }

        String c = sc.next();

        if (c.equals("Y") || c.equals("y")) { // Y를 누르면 keep의 배열에 각각의 주사위 눈을 삽입
            dices.clear();
            for (int i = 0; i < keep.size(); i++) {
                dices.add(keep.get(i));

            }
            return true;
        }

        for (int i = 0; i < 5; i++) { // 음수인 수들은 다시 절댓값을 취함
            if (dices.get(i) < 0)
                dices.set(i, dices.get(i) * -1);

        }

        return false;

    }

}
