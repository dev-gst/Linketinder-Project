export class ChartData {
    keys: string[] = []
    values: number[] = []

    public get(k: string): number | null {
        for (let i = 0; i < this.keys.length; i++) {
            if (this.keys[i] === k) {
                return this.values[i];
            }
        }

        return null;
    }

    public increment(k: string): void {
        for (let i = 0; i < this.keys.length; i++) {
            if (this.keys[i] === k) {
                this.values[i]++;
                return;
            }
        }

        this.set(k, 1);
    }

    private set(k: string, v: number): void {
        this.keys.push(k);
        this.values.push(v);
    }

    public toString(): string {
        let s: string = '';
        for (let i = 0; i < this.keys.length; i++) {
            s += `key: ${this.keys[i]}, value: ${this.values[i]}\n`
        }

        return s;
    }
}