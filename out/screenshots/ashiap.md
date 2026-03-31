
```sh
for file in src/*.java; do name=$(basename "$file" .java); freeze "$file" -o "out/screenshots/$name.png" --font.family "JetBrainsMonoNL Nerd Font"; done
```

for src/exceptions
```sh
for file in src/exceptions/*.java; do name=$(basename "$file" .java); freeze "$file" -o "out/screenshots/exceptions/$name.png" --font.family "JetBrainsMonoNL Nerd Font"; done
```

