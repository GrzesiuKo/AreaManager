package Diagram;

public class Item {
    private Item parent;
    private Item left;
    private Item right;

    public static void replaceParent(Item source, Item destination) {
        Item sourceParent = source.getParent();

        if (sourceParent == null){
            destination.setParent(null);
            Voronoi.root = destination;
            return;
        }

        if (sourceParent.getLeft() == source) {
            sourceParent.setLeft(destination);
        } else {
            sourceParent.setRight(destination);
        }
        destination.setParent(sourceParent);
        return 0;
    }

    public Item getParent() {
        return parent;
    }

    public void setParent(Item parent) {
        this.parent = parent;
    }

    public Item getLeft() {
        return left;
    }

    public void setLeft(Item left) {
        this.left = left;
        left.setParent(this);
    }

    public Item getRight() {
        return right;
    }

    public void setRight(Item right) {
        this.right = right;
        right.setParent(this);
    }
}
