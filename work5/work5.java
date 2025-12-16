import java.io.*;
import java.util.*;

public class work5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); //キーボード入力を受け取る

        while (true) { //ユーザが終了命令を出すまで∞ループ
            System.out.print(">"); //プロンプト表示

            String line = sc.nextLine().trim(); //1行読み込み、前後の空白を削除
            if (line.length() == 0) { //空行なら再度プロンプト表示へ
                continue;
            }

            String[] cmd = line.split("\\s+"); //空白で分割してコマンドと引数に分ける

            // x : 終了
            if (cmd[0].equals("x")) {
                System.out.println("おわり"); //終了メッセージ表示
                break;
            }

            // r ファイル名 : 読み込み
            else if (cmd[0].equals("r")) {
                if (cmd.length != 2) { //引数の数をチェック
                    System.err.println("Command Error"+e.getMessage());
                    continue;
                }
                // ファイルを開いて内容を標準出力に書き出す
                try (FileInputStream inFile = new FileInputStream(cmd[1])) {
                    byte[] buff = new byte[1024];
                    int n;
                    while ((n = inFile.read(buff)) != -1) {
                        System.out.write(buff, 0, n);
                    }
                    System.out.flush(); // バッファの内容を強制的に出力
                } catch (FileNotFoundException e) { // ファイルが見つからない場合
                    System.err.println("File Not Found: " + cmd[1]);
                } catch (IOException e) { // その他の入出力エラー
                    System.err.println("IO Error: " + e.getMessage());
                }
            }

            // w ファイル名 : 書き込み
            else if (cmd[0].equals("w")) {
                if (cmd.length != 2) { //  引数の数をチェック
                    System.err.println("Command Error");
                    continue;
                }
                // ファイルを開いてキーボードからの入力を書き込む
                try (FileOutputStream outfile = new FileOutputStream(cmd[1])) {
                    while (true) {
                        String input = sc.nextLine(); // キーボードから1行読み込み
                        if (input.startsWith("bye")) { // "bye"で終了
                            break;
                        }
                        outfile.write((input + System.lineSeparator())
                                .getBytes("UTF-8"));
                    }
                } catch (IOException e) {
                    System.err.println("IO Error: " + e.getMessage());
                }
            }

            // その他
            else {
                System.err.println("Command Error");
            }
        }
        sc.close();
    }
}
