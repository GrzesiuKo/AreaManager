package Diagram;

public class Item {
    private Item parent;
    private Item left;
    private Item right;

    public Item getNearestLeftArc(){
        Item result = left;

        if (left != null){
            while(result instanceof Cross){
                result = result.getRight();
            }
        }
        return result;
    }

    public Item getNearestRightArc(){
        Item result = right;

        if (right != null){
            while(result instanceof Cross){
                result = result.getLeft();
            }
        }
        return result;
    }

    public Item getParent() {
        return parent;
    }

    public Item getLeft() {
        return left;
    }

    public Item getRight() {
        return right;
    }
}
