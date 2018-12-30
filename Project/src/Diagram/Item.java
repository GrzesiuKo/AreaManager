package Diagram;

public class Item {
    private Item parent;
    private Item left;
    private Item right;

    public static int replaceParent(Item source, Item destination) {
        Item sourceParent = source.getParent();

        if (sourceParent == null) {
            destination.setParent(null);
            return 1;
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

    public void printTree(Item item) {
        System.out.println("Parent: " + item.getClass());
        if (item.getLeft() != null) {
            System.out.println("    Left: " + item.getLeft().getClass());
            printTree(item.getLeft());
        }else{
            System.out.println("    Left: null");
        }

        if (item.getRight()!=null) {

            System.out.println("    Right: " + item.getRight().getClass());
            printTree(item.getRight());
        }else{
            System.out.println("    Right: null");
        }
    }
}
