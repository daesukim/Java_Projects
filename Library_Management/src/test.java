public class test {
    class A {
        int m() { return 0; }
        int f() { return 10 + this.g(); }
        int g() { return 100; }
    }
    class B extends A {
        int g() { return 1000; }
        int f() { return 15 + super.f() + this.g(); }
    }
    class C extends B {
        int m() { return this.f() + 1; }
        int g () { return 10000; }
    }
    public void main(String[] args) {
        A x = new C();
        System.out.println(x.m());
    }
}
