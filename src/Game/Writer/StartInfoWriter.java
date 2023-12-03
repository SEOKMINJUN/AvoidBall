package Game.Writer;

import javax.swing.*;

public class StartInfoWriter extends JFrame{
	public String name = "";
	public int diff = 0;
	public StartInfoWriter() {
		//이름 받아오기(조건 만족할때까지 무한 루프)
		String name = null;
		int diff = 0;
		while(name == null || !(isAlphabet(name) && name.length() <= 6)) {
			JPanel input_panel = new JPanel();
			JLabel input_message = new JLabel("플레이어 이름을 입력해주세요(알파벳만 가능, 최대 6자), 공백일시 이름이 NONAME으로 결정됩니다.");
			JTextField input_field = new JTextField(1);
			input_panel.setLayout(new BoxLayout(input_panel, BoxLayout.Y_AXIS));
			input_panel.add(input_message);
			input_panel.add(input_field);
			Object[] option = {"OK"};
			JOptionPane.showOptionDialog(null, input_panel, "사용자 정보 입력", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,null,option,option[0]);
			name = input_field.getText();
			
			if(name == null || name.isBlank()){
				name = "NONAME";
			}
		}
		System.out.printf("%s \n",name);
		
		//난이도 선택
		
		while((diff = JOptionPane.showConfirmDialog(null, "예를 선택하면 쉬운 난이도, 아니요를 선택하면 어려운 난이도로 시작합니다.", "난이도 선택", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE)) < 0)
		System.out.println(Integer.toString(diff));
		
		this.name = name;
		this.diff = diff;
	}
	
	//문자열이 알파벳만 포함하고 있는게 맞는지 확인
	public boolean isAlphabet(String name) {
	    char[] char_array = name.toCharArray();
	    for (int i=0;i<char_array.length;i++) {
	        if(!Character.isAlphabetic(char_array[i])) {
	            return false;
	        }
	    }
	    return true;
	}
}
