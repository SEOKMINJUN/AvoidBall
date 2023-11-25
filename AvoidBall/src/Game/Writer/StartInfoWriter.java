package Game.Writer;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class StartInfoWriter extends JFrame{
	private String name = "";
	private int diff = 0;
	public StartInfoWriter() {
		String name = "1";
		int diff = 0;
		while(!isAlphabet(name) && name.length() <= 6) {
			name = JOptionPane.showInputDialog(null, "플레이어 이름을 입력해주세요(알파벳만 가능, 최대 6자), 공백일시 이름이 NONAME으로 결정됩니다.", "사용자 정보 입력", JOptionPane.OK_OPTION);
			if(name == "")
				name = "NONAME";
		}
		
		System.out.println(name);
		
		diff = JOptionPane.showConfirmDialog(null, "예를 선택하면 쉬운 난이도, 아니요를 선택하면 어려운 난이도로 시작합니다.", "난이도 선택", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
		System.out.println(Integer.toString(diff));
		
		this.name = name;
		this.diff = diff;
	}
	
	public boolean isAlphabet(String name) {
	    char[] char_array = name.toCharArray();

	    for (int i=0;i<char_array.length;i++) {
	        if(!Character.isLetter(char_array[i])) {
	            return false;
	        }
	    }
	    return true;
	}
}
