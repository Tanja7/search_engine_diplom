public class PageEntry implements Comparable<PageEntry> {
// Класс, описывающий один элемент результата одного поиска.

    // имя пдф-файла,
    private final String pdfName;
    // номер страницы
    private final int page;
    // количество раз, которое встретилось слово на странице
    private int count;

    public PageEntry(String pdfName, int page, int count) {
        this.pdfName = pdfName;
        this.page = page;
        this.count = count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPdfName() {
        return pdfName;
    }

    public int getPage() {
        return page;
    }
    public int getCount() {
        return count;
    }

    // сортировка по количеству раз, от большего к меньшему
    @Override
    public int compareTo(PageEntry o) {
        return Integer.compare(o.count, count);
    }

    @Override
    public String toString() {
        return "PageEntry{" +
                "pdfName='" + pdfName + '\'' +
                ", page=" + page +
                ", count=" + count +
                '}';
    }

    @Override
    public int hashCode() {
        return pdfName.hashCode() + page + count;
    }

}
