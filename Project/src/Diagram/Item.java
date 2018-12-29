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
    }

    public Arc getNearestLeftArc() {
        Item result = left;

        if (left != null) {
            while (result instanceof Cross) {
                result = result.getRight();
            }
        }
        return (Arc)result;
    }

    public Arc getNearestRightArc() {
        Item result = right;

        if (right != null) {
            while (result instanceof Cross) {
                result = result.getLeft();
            }
        }
        return (Arc)result;
    }

    public Cross getNearestLeftCross(){
        Item parent = this.getParent();
        Item child = this;

        if (parent == null){
            return null;
        }

        while(parent.getLeft() == child){
            if (parent.getParent() == null){
                return null;
            }else{
                child = parent;
                parent = parent.getParent();
            }
        }
        return (Cross)parent;
    }

    public Cross getNearestRightCross(){
        Item parent = this.getParent();
        Item child = this;

        if (parent == null){
            return null;
        }

        while(parent.getRight() == child){
            if (parent.getParent() == null){
                return null;
            }else{
                child = parent;
                parent = parent.getParent();
            }
        }
        return (Cross)parent;
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
