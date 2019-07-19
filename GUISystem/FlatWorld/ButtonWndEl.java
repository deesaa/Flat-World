package FlatWorld;

public class ButtonWndEl extends WindowElement{
	ButtonWndEl(int leftTopVertexX, int leftTopVertexY, int rightBottomVertexX, int rightBottomVertexY){
		super(leftTopVertexX, leftTopVertexY, rightBottomVertexX, rightBottomVertexY);
	}
	
	public void rend(int LTXShift, int LTYShift, int RBXShift, int RBYShift){
		super.rend(LTXShift, LTYShift, RBXShift, RBYShift);
	}
}
