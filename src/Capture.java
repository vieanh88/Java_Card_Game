import java.util.List;

public abstract class Capture {
    // Danh sách các lá bài được chọn
    protected List<Card> choosenCards;
    
    // Constructor: Khởi tạo danh sách bài
    public Capture(List<Card> choosenCards) {
    	this.choosenCards = choosenCards;
    }

    // Phân loại bộ bài theo quy tắc game
    public String getCaptureName() {
    	return GameRule.classify(this.choosenCards);
    }
    
    // Trả về danh sách bài đã chọn
    public List<Card> getChoosenCard(){
    	return this.choosenCards;
    }
}