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

    public static void replaceParent(Item source, Item destination){
        Item sourceParent = source.getParent();

        if (sourceParent.getLeft() == source){
            sourceParent.setLeft(destination);
        }else {
            sourceParent.setRight(destination);
        }
        destination.setParent(sourceParent);
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

    public void setParent(Item parent) {
        this.parent = parent;
    }

    public void setLeft(Item left) {
        this.left = left;
        left.setParent(this);
    }

    public void setRight(Item right) {
        this.right = right;
        right.setParent(this);
    }
}
