import javafx.geometry.*;
import javafx.scene.image.*;
import javafx.scene.layout.StackPane;

public class CardPane extends StackPane {
    // Thuộc tính quản lý trạng thái của ô bài
    private Player currentPlayer;
    private Card card;
    private ImageView iv;
    private boolean selected = false;
    private boolean active = true;

    // Constructor: Tạo ô bài với chiều rộng cố định
    public CardPane(int width) {
        iv = new ImageView();
        iv.setFitWidth(width);
        iv.setPreserveRatio(true);
        iv.setSmooth(true);
        iv.setCache(true);

        setMargin(iv, new Insets(4));
        getChildren().add(iv);
    }

    // Đặt lá bài và quản lý sự kiện click
    public void setCard(Card card) {
        this.card = card;
        selected = false;
        setStyle("-fx-background-color: none");

        if(card == null) {
            iv.setImage(null);
            setOnMouseClicked(null);
        } else {
            iv.setImage(card.getImage());

            setOnMouseClicked(e -> {
                if(!active) return;

                if(!selected) {
                    // Chọn bài: nền đỏ, thêm vào danh sách bài chọn
                    setStyle("-fx-background-color: red");
                    currentPlayer.addChoosenCard(card);
                } else {
                    // Bỏ chọn: trả lại nền gốc, xóa khỏi danh sách
                    setStyle("-fx-background-color: none");
                    currentPlayer.removeChoosenCard(card);
                }

                selected = !selected;
            });
        }
    }

    // Đặt người chơi hiện tại cho ô bài
    public void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    // Kích hoạt/vô hiệu hóa ô bài
    public void setActive(boolean active) {
        this.active = active;

        if(card == null) return;

        // Thay đổi hình ảnh khi không active
        if(!active)
            iv.setImage(card.getBackImage());
        else
            iv.setImage(card.getImage());
    }
}