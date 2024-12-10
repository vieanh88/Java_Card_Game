import java.util.Random;
import java.util.Comparator;
import javafx.scene.image.*;
import java.net.URL;

// Lớp Card đại diện cho một lá bài trong game
public class Card implements Comparable<Card>{
    // Biến để lưu trữ giá trị suit (chất) và rank (điểm) của lá bài
    private int suit, rank;

    // Mảng để chuyển đổi giá trị suit từ số sang từ ngữ
    // Thứ tự: Bích (Spade), Chuồn (Club), Rô (Diamond), Cơ (Heart)
    private final String[] verbose_suit = {"Spade", "Club", "Diamond", "Heart"};

    // Mảng để chuyển đổi giá trị rank từ số sang từ ngữ
    // Thứ tự từ thấp đến cao: 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K, A, 2
    private final String[] verbose_rank = {"3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "2"};

    // Mảng lưu trữ ký hiệu Unicode của các chất bài
    private final String[] suit_symbol = {"\u2660", "\u2663", "\u2662", "\u2661"};

    // Biến lưu trữ hình ảnh của lá bài
    private Image cardImage;

    // Lá bài rỗng - được sử dụng trong các trường hợp đặc biệt
    private static Card emptyCard = new Card(-1);

    // Hình ảnh mặt sau của lá bài
    private static Image backImage = new Image(Card.class.getResourceAsStream("img/back.png"));

    // Constructor: Tạo lá bài từ giá trị số (0-51)
    // Mỗi giá trị đại diện cho một lá bài duy nhất trong bộ bài
    public Card(int value){
        // Kiểm tra giá trị hợp lệ (0-51)
        if(0 <= value && value <= 51){
            // Tính toán suit: chia nguyên để xác định chất bài
            // Ví dụ: 0-12 là Bích, 13-25 là Chuồn, v.v.
            suit = value/13;

            // Tính toán rank: lấy phần dư để xác định điểm bài
            // Ví dụ: 0%13 = 0 (3 Bích), 14%13 = 1 (4 Chuồn)
            rank = value%13;
        }
    }

    // Phương thức trả về tên chất bài bằng chữ
    public String getSuit(){
        return verbose_suit[suit];
    }

    // Phương thức trả về giá trị số của chất bài
    public int getSuitValue(){
        return suit;
    }

    // Phương thức trả về tên điểm bài bằng chữ
    public String getRank(){
        return verbose_rank[rank];
    }

    // Phương thức trả về giá trị số của điểm bài
    public int getRankValue(){
        return rank+1;
    }

    // Phương thức toString để hiển thị lá bài
    // Kết hợp ký hiệu Unicode và tên điểm bài
    public String toString(){
        return suit_symbol[suit]+verbose_rank[rank];
    }

    // Phương thức tạo một bộ bài mới 52 lá
    public static Card[] newDeck(){
        // Tạo mảng 51 lá bài (có vẻ là do một lỗi nhỏ trong code)
        Card[] deck = new Card[51];
        
        // Tạo từng lá bài với giá trị từ 0-51
        for(int i = 0; i < deck.length; i++){
            deck[i] = new Card(i);
        }
        return deck;
    }

    // Phương thức xáo trộn bộ bài
    public static Card[] shuffleDeck(Card[] deck){
        // Tạo mảng mới để lưu bộ bài đã xáo trộn
        Card[] shuffled = new Card[deck.length];

        // Sử dụng Random để tạo tính ngẫu nhiên
        Random rand = new Random();
        
        // Vòng lặp để chọn ngẫu nhiên từng lá bài
        for(int i = 0; i < deck.length; i++){
            // Chọn ngẫu nhiên một lá bài còn lại
            int nextCard = rand.nextInt(deck.length-i);
            
            // Tìm và di chuyển lá bài được chọn
            for(int j = 0; j < deck.length; j++){
                // Nếu đây là lá bài được chọn và chưa được chọn
                if(nextCard == 0 && deck[j] != null){
                    shuffled[i] = deck[j];
                    deck[j] = null;
                    break;
                }

                // Giảm nextCard nếu gặp lá bài chưa được chọn
                if(deck[j] != null)
                    nextCard--;
            }
        }

        return shuffled;
    }

    // Phương thức so sánh để sắp xếp bài
    // Ưu tiên sắp xếp theo điểm, sau đó theo chất
    public int compareTo(Card c){
        // Nếu điểm khác nhau, so sánh theo điểm
        if(this.rank != c.rank)
            return this.rank - c.rank;
        
        // Nếu điểm giống nhau, so sánh theo chất
        return this.suit - c.suit;
    }

    // Phương thức trả về lá bài rỗng
    public static Card getEmptyCard(){
        return emptyCard;
    }

    // Phương thức lấy hình ảnh của lá bài
    // Sử dụng kỹ thuật lazy loading để tối ưu hiệu năng
    public Image getImage(){
        // Chỉ tải hình ảnh khi được yêu cầu
        if(cardImage == null)
            cardImage = new Image(
                // Đường dẫn tới file ảnh của lá bài
                Card.class.getResourceAsStream(("img/" + verbose_rank[rank]+ verbose_suit[suit] + ".png").toLowerCase()));
        return cardImage;
    }

    // Phương thức lấy hình ảnh mặt sau của lá bài
    public static Image getBackImage(){
        return backImage;
    }
}
