package io.github.key_del_jeeinho.golabab_v2.account_server;

public class ConsoleManager {
    public static void sendWarningMessage(String message, int seconds) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
        System.out.println(ConsoleColor.YELLOW +
                "db   d8b   db  .d8b.  d8888b. d8b   db d888888b d8b   db  d888b  db \n" +
                "88   I8I   88 d8' `8b 88  `8D 888o  88   `88'   888o  88 88' Y8b 88 \n" +
                "88   I8I   88 88ooo88 88oobY' 88V8o 88    88    88V8o 88 88      YP \n" +
                "Y8   I8I   88 88~~~88 88`8b   88 V8o88    88    88 V8o88 88  ooo    \n" +
                "`8b d8'8b d8' 88   88 88 `88. 88  V888   .88.   88  V888 88. ~8~ db \n" +
                " `8b8' `8d8'  YP   YP 88   YD VP   V8P Y888888P VP   V8P  Y888P  YP \n" +
                "                                                                    ");
        System.out.println(ConsoleColor.RED_BRIGHT + message);
        System.out.println(ConsoleColor.RESET);
        Thread.sleep(seconds * 1000L);
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }
}
