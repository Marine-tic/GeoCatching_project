package iut.unice.fr.geocatching.Models;



import java.util.ArrayList;
import java.util.List;

import iut.unice.fr.geocatching.R;


/**
 * This class manages the list of all figures that can be used in the game.
 */
public class FigureList {
    private List<Figure> figureList;
    private Figure oval;
    private Figure polygon;
    private Figure triangle;
    private Figure square;


    public FigureList(){
        oval = new Figure(0,"Oval", R.drawable.oval, R.drawable.oval_small, R.id.ovalButton, R.raw.oval_sound);
        polygon = new Figure(1,"Polygon", R.drawable.polygon, R.drawable.polygon_small, R.id.polygonButton, R.raw.polygon_sound);
        triangle = new Figure(2,"Triangle", R.drawable.triangle, R.drawable.triangle_small, R.id.triangleButton, R.raw.triangle_sound);
        square = new Figure(3,"Square", R.drawable.square, R.drawable.square_small, R.id.squareButton, R.raw.square_sound);
        figureList = new ArrayList<>();
        figureList.add(oval);
        figureList.add(polygon);
        figureList.add(triangle);
        figureList.add(square);
    }
    public int size(){
        return figureList.size();
    }


    public List<Figure> getFigureList() {
        return figureList;
    }

    public void setFigureList(List<Figure> figureList) {
        this.figureList = figureList;
    }

    public int getSizeFigureList(){
        return figureList.size();
    }

    public int getIndexOfFigureInFigureList(Figure figure){
        return figureList.indexOf(figure);
    }

    public Figure getFigureFromIndexInFigureList(int index){
        return figureList.get(index);
    }

    public Figure getFigureFromNameInFigureList(String name){
        for (Figure figure:figureList) {
            if(figure.getName().equals(name)){
                return figure;
            }
        }
        return null;
    }
}
