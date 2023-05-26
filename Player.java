package Yacht;

import java.util.List;

public class Player {

    static final String[] ZoneName = { 	"One", "Two", "Three", "Four", "Five", "Six",
            "Choice", "Four of a Kind", "FullHouse",
            "Small Straight", "Large Straight", "Yacht"};

    String _playerName = "player1";

    int _zoneScore[] = new int[13];
    boolean _zoneCheck[] = new boolean[13];

    // 1등 플레이어를 가리기 위한 함수
    public static String GetWinnderName(List<Player> players) {
        int topScoreIdx = 0;
        int topScore = players.get(0).SumScore();
        for (int i = 1; i < players.size(); i++) {
            int score = players.get(i).SumScore();
            if (score > topScore) {
                topScore = score;
                topScoreIdx = i;
            }
        }
        return players.get(topScoreIdx)._playerName;
    }
    // 각 플레이어의 점수를 합함
    public int SumScore() {
        int totalScore = 0;
        for (int i = 0; i < 13; i++) {
            totalScore += _zoneScore[i];
        }
        return totalScore;

    }


    public Player(String name) {
        _playerName = name;

        GameManager.GetInstance().AddPlayer(this);
    }
    // 플레이어 생성자
    public String GetName() {
        return _playerName;

    }
    // 각 플레이어에게 해당되는 모든 항목의 점수 출력
    public void PrintDeck(int[] data) {
        for (int i = 0; i < 12; i++) {
            if (_zoneCheck[i])
                continue;

            _PrintZoneScore(i, _GetZoneScore(i, data));

        }
        System.out.println();

    }

    public void PrintSelect() {
        GameManager.PrintFontCyan("원하는 항목을 선택하세요.");
        if (_zoneCheck[0] == false) {
            GameManager.PrintFontCyan("\nOne은 숫자 1을, ");
        }
        if (_zoneCheck[1] == false) {
            GameManager.PrintFontCyan("Two는 숫자 2를, ");
        }
        if (_zoneCheck[2] == false) {
            GameManager.PrintFontCyan("Three는 숫자 3을, ");
        }
        if (_zoneCheck[3] == false) {
            GameManager.PrintFontCyan("Four는 숫자 4를, ");
        }
        if (_zoneCheck[4] == false) {
            GameManager.PrintFontCyan("Five는 숫자 5를, ");
        }
        if (_zoneCheck[5] == false) {
            GameManager.PrintFontCyan("Six는 숫자 6을, ");
        }
        if (_zoneCheck[6] == false) {
            GameManager.PrintFontCyan("Choice은 알파벳 C를, ");
        }
        if (_zoneCheck[7] == false) {
            GameManager.PrintFontCyan("Four of a kind는 알파벳 F를, ");
        }
        if (_zoneCheck[8] == false) {
            GameManager.PrintFontCyan("Full house는 알파벳 H를, ");
        }
        if (_zoneCheck[9] == false) {
            GameManager.PrintFontCyan("Small Straight는 알파벳 S를, ");
        }
        if (_zoneCheck[10] == false) {
            GameManager.PrintFontCyan("Large Straight는 알파벳 L를, ");
        }
        if (_zoneCheck[11] == false) {
            GameManager.PrintFontCyan("Yacht는 알파벳 Y를 ");
        }
        GameManager.PrintFontCyan("누르세요. (누르는 순간 수정 불가) : ");
    }

    public boolean SetScore(Player nowPlayer, int[] data, String res) {
        int zoneIdx = _FindInputIdx(res);

        if (zoneIdx < 0) {
            GameManager.PrintFontCyanln("잘못 입력하셨습니다. 다시 입력해주세요.");
            return false;
        }
        // 플레이어가 선택한 항목에 맞는 점수 score
        int score = _GetZoneScore(zoneIdx, data);
        // 플레이어가 해당 항목을 선택했기 때문에 zoneCheck에서 false를 true로 바꾸고, 해당 인덱스의 점수를 삽입
        _zoneCheck[zoneIdx] = true;
        _zoneScore[zoneIdx] = score;

        GameManager.PrintFontCyanln("\n" + _playerName + "님의 " + ZoneName[zoneIdx] +
                " 자리에 값 " + score + "을(를) 넣었습니다.");
        // One~Six의 보너스 점수를 얻지 못했을 때 출력하는 메시지
        if (!_zoneCheck[12]) {
            int numSum = _NumScoreSum();
            GameManager.PrintFontCyan("현재 당신의 One~Six의 합은 " + numSum + "입니다.");

            if (numSum >= 63) {
                System.out.println("\n★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆");
                GameManager.PrintFontCyanln(nowPlayer + "님의 One~Six의 총합이 63을 넘어섰으므로 추가 점수 35점을 획득했습니다.");
                System.out.print("★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆");
                _zoneScore[12] = 35;
                _zoneCheck[12] = true;
            }
        }

        return true;
    }
    // result 변수(GetZoneScore 함수에 쓰임) 체크 용도 함수
    private int _FindInputIdx(String res) {

        int result = -1;

        if (res.equals("1")) {
            result = 0;
        }
        else if (res.equals("2")) {
            result = 1;
        }
        else if (res.equals("3")) {
            result = 2;
        }
        else if (res.equals("4")) {
            result = 3;
        }
        else if (res.equals("5")) {
            result = 4;
        }
        else if (res.equals("6")) {
            result = 5;
        }
        else if (res.equals("C") || res.equals("c")) {
            result = 6;
        }
        else if (res.equals("F") || res.equals("f")) {
            result = 7;
        }
        else if (res.equals("H") || res.equals("h")) {
            result = 8;
        }
        else if (res.equals("S") || res.equals("s")) {
            result = 9;
        }
        else if (res.equals("L") || res.equals("l")) {
            result = 10;
        }
        else if (res.equals("Y") || res.equals("y")) {
            result = 11;
        }

        if (result < 0 || _zoneCheck[result])
            return -1;

        return result;
    }
    // 보너스 점수를 얻기 위해 One~Six의 총합 매기는 함수
    private int _NumScoreSum() {
        int num = 0;
        for (int i = 0; i < 6; i++) {
            if (_zoneCheck[i] == true)
                num += _zoneScore[i];
        }
        return num;
    }


    // counter 배열로 각각의 항목을 체크
    static private int _GetZoneScore(int zoneIdx, int[] counter) {
        switch(zoneIdx) {
            case 0:
                return _CheckNum(counter, 0);
            case 1:
                return _CheckNum(counter, 1);
            case 2:
                return _CheckNum(counter, 2);
            case 3:
                return _CheckNum(counter, 3);
            case 4:
                return _CheckNum(counter, 4);
            case 5:
                return _CheckNum(counter, 5);
            case 6:
                return _Choice(counter);
            case 7:
                return _FofaKind(counter);
            case 8:
                return _FullHouse(counter);
            case 9:
                return _SStraight(counter);
            case 10:
                return _LStraight(counter);
            case 11:
                return _Yacht(counter);

        }
        return -1;

    }
    // One~Six 점수 산정
    static private int _CheckNum(int[] c, int zoneIdx) {
        return c[zoneIdx] * (zoneIdx + 1);

    }

    static private int _Choice(int[] c) {
        int num = 0;
        for (int i = 0; i < 6; i++) {
            num += _CheckNum(c, i);
        }
        return num;
    }

    static private int _FofaKind(int[] c) {
        int num = 0;

        for (int i = 0; i < 6; i++) {
            if (c[i] >= 4) {
                for (int j = 0; j < 6; j++) {
                    num += _CheckNum(c, j);
                }
            }
        }

        return num;
    }

    static private int _FullHouse(int[] c) {
        int num = 0;
        boolean check2 = false;
        boolean check3 = false;

        for (int i = 0; i < 6; i++) {
            if (c[i] == 2) {
                check2 = true;
            } else if (c[i] == 3) {
                check3 = true;
            }
        }

        if (check2 && check3) {
            for (int i = 0; i < 6; i++) {
                num += _CheckNum(c, i);
            }
            return num;
        }

        return 0;
    }

    static private int _ContinuityCount(int[] c) {
        int result = 0;
        int continuityCount = 0;
        int idx = 0;

        for (; idx < 6; idx++) {
            for (; idx < 6; idx++) {
                if (c[idx] == 0) {
                    if (result < continuityCount)
                        result = continuityCount;
                    continuityCount = 0;
                    break;
                }
                continuityCount++;
            }

        }
        if (result < continuityCount)
            result = continuityCount;
        return result;
    }

    static private int _SStraight(int[] c) {
        if (_ContinuityCount(c) > 3) {
            return 15;
        }
        return 0;
    }

    static private int _LStraight(int[] c) {
        if (_ContinuityCount(c) > 4) {
            return 30;
        }
        return 0;
    }

    static private int _Yacht(int[] c) {

        for (int i = 1; i < 5; i++) {
            if (c[i] == 5)
                return 50;

        }
        return 0;
    }


    private void _PrintZoneScore(int zoneIdx, int score) {
        System.out.print(_playerName + "의 " + ZoneName[zoneIdx]+"에 들어갈 수는 " + score + "입니다. ");

        if (score != 0) {
            GameManager.PrintFontRedln("★");
        }
        else System.out.println();
    }


}
