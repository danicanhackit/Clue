import javax.swing.ImageIcon;

public class Checkbox extends Button{
    private boolean checked;
    public Checkbox(){
        super();
        checked = false;
    }
    public Checkbox(int x, int y){
        super(x, y, 50, 50, new ImageIcon("Button Images\\blankcheckbox.png"));
        checked = false;
    }
    public void checkForChecked(){
        if(checked){
            setImage(new ImageIcon("Button Images\\checkedbox.png"));
        }
    }
    public void setChecked(boolean checked){
        this.checked = checked;
    }
    public boolean isChecked(){
        return checked;
    }
}